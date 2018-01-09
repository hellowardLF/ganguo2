package tv.ganguo.live.home.model;

/**
 * Function：
 * Created by lijiefenf on 2017/12/23.
 */

public class DaiKuanModle {

    /**
     * id : 1
     * name : 小额网贷
     * quota : 500-3000
     * description : 小额网贷
     * sort_id : 1
     * status : 1
     * create_time : 1513926440
     * update_time : 1513931631
     */

    private String id;
    private String name;
    private String quota;
    private String description;
    private String sort_id;
    private String status;
    private String create_time;
    private String update_time;
    private int isClick;

    public int getIsClick() {
        return isClick;
    }

    public void setIsClick(int isClick) {
        this.isClick = isClick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
