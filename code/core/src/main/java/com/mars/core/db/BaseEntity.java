package com.mars.core.db;

public class BaseEntity implements IEntity {
    private long id;
    private long uid;

    @Override
    public long getId() {
        return this.getId();
    }

    @Override
    public long getUid() {
        return this.getUid();
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTableKey() {
        return getSerializeKey(this.getUid(), this.getClass());
    }

    protected String getSerializeKey(long uid, Class<? extends BaseEntity> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName());
        sb.append("-");
        sb.append(uid);
        return sb.toString();
    }
}
