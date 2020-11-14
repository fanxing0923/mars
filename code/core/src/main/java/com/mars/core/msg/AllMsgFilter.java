/**
 * @program: mars
 * @description:
 * @author: xing.fan
 * @create: 2020-11-06 17:45
 **/
package com.mars.core.msg;

import com.google.common.collect.Lists;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.GeneratedMessage;
import com.mars.core.db.impl.BytesUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
public class AllMsgFilter {
    @RequestMapping("msg")
    public String receive(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long uid = 0;
        String uidStr = request.getParameter("uid");
        if (uidStr != null) {
            uid = Long.parseLong(uidStr);
        }
        InputStream is = null;
        DataInputStream dis = null;
        OutputStream os = null;
        try {
            is = request.getInputStream();
            dis = new DataInputStream(is);
            short length = dis.readShort();
            int type = dis.readInt();
            byte[] data = new byte[length];
            dis.read(data);
            MsgEntry msgEntry = MsgManager.getIns().getActionImpl(type);
            GeneratedMessage msgContent = (GeneratedMessage) msgEntry.getValue()
                    .getParseFrom().invoke(null, data);
            List<AbstractMessage.Builder> backList = Lists.newArrayList();
            msgEntry.getKey().invoke(msgEntry.getValue(), 1001,
                    msgContent, backList);
            if (backList != null && backList.size() == 0) {
                return "1";
            }
            os = response.getOutputStream();
            byte[] backArray = new byte[128];
            int index = 0;
            for (AbstractMessage.Builder builder : backList) {
                int msgType = MsgManager.getIns().getMsgType(builder.getClass());
                byte[] req = builder.build().toByteArray();
                byte[] lengthArray = BytesUtil.shortToByteArray((short) req.length);
                byte[] typeArray = BytesUtil.intToByteArray(msgType);
                if (backArray.length - index <= req.length + 6) {
                    byte[] newSpace = new byte[backArray.length + req.length +6+128];
                    System.arraycopy(backArray, 0, newSpace, 0, index);
                    backArray = newSpace;
                }

                backArray[index + 0] = lengthArray[0];
                backArray[index + 1] = lengthArray[1];
                backArray[index + 2] = typeArray[0];
                backArray[index + 3] = typeArray[1];
                backArray[index + 4] = typeArray[2];
                backArray[index + 5] = typeArray[3];
                System.arraycopy(req, 0, backArray, index + 6, req.length);
                index += req.length + 6;
            }
            response.setContentLength(backArray.length);
            os.write(backArray);
            os.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (dis != null) {
                dis.close();
            }
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return "1";
    }
}