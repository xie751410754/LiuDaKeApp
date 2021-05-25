package com.cdxz.liudake.bean;

import java.util.List;

public class JDCategoryMenuDto {

    /**
     * code : 1
     * msg :
     * count : 10
     * data : [{"ID":"28","CatId":"11729","Count":"1","ParentId":"0","field":"cid1","Name":"鞋靴","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"1","sTitle":"鞋靴"},{"ID":"1","CatId":"1320","Count":"1381","ParentId":"0","field":"cid1","Name":"食品饮料","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:46","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:48","MainPic":"http://liudake.cn/imgUploads/20210524064928492843756.jpg","Index":"2","sTitle":"食品"},{"ID":"11","CatId":"9987","Count":"187","ParentId":"0","field":"cid1","Name":"手机通讯","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"3","sTitle":"手机"},{"ID":"10","CatId":"652","Count":"270","ParentId":"0","field":"cid1","Name":"数码","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"4","sTitle":"数码"},{"ID":"7","CatId":"1316","Count":"519","ParentId":"0","field":"cid1","Name":"美妆护肤","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"5","sTitle":"美妆"},{"ID":"2","CatId":"737","Count":"805","ParentId":"0","field":"cid1","Name":"家用电器","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:51","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"6","sTitle":"家电"},{"ID":"6","CatId":"670","Count":"559","ParentId":"0","field":"cid1","Name":"电脑、办公","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"7","sTitle":"办公"},{"ID":"5","CatId":"1319","Count":"651","ParentId":"0","field":"cid1","Name":"母婴","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"8","sTitle":"母婴"},{"ID":"18","CatId":"1318","Count":"54","ParentId":"0","field":"cid1","Name":"运动户外","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"9","sTitle":"运动"},{"ID":"22","CatId":"6233","Count":"21","ParentId":"0","field":"cid1","Name":"玩具乐器","weight":"0","Deep":"1","CreateTime":"2021/3/26 10:53:54","State":"1","IsDel":"0","UpdateTime":"2021/3/26 10:53:54","MainPic":"http://liudake.cn/cat.jpg","Index":"10","sTitle":"玩具"}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * ID : 28
         * CatId : 11729
         * Count : 1
         * ParentId : 0
         * field : cid1
         * Name : 鞋靴
         * weight : 0
         * Deep : 1
         * CreateTime : 2021/3/26 10:53:54
         * State : 1
         * IsDel : 0
         * UpdateTime : 2021/3/26 10:53:54
         * MainPic : http://liudake.cn/cat.jpg
         * Index : 1
         * sTitle : 鞋靴
         */

        private String ID;
        private String CatId;
        private String Count;
        private String ParentId;
        private String field;
        private String Name;
        private String weight;
        private String Deep;
        private String CreateTime;
        private String State;
        private String IsDel;
        private String UpdateTime;
        private String MainPic;
        private String Index;
        private String sTitle;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCatId() {
            return CatId;
        }

        public void setCatId(String CatId) {
            this.CatId = CatId;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String Count) {
            this.Count = Count;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDeep() {
            return Deep;
        }

        public void setDeep(String Deep) {
            this.Deep = Deep;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getIsDel() {
            return IsDel;
        }

        public void setIsDel(String IsDel) {
            this.IsDel = IsDel;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getMainPic() {
            return MainPic;
        }

        public void setMainPic(String MainPic) {
            this.MainPic = MainPic;
        }

        public String getIndex() {
            return Index;
        }

        public void setIndex(String Index) {
            this.Index = Index;
        }

        public String getSTitle() {
            return sTitle;
        }

        public void setSTitle(String sTitle) {
            this.sTitle = sTitle;
        }
    }
}
