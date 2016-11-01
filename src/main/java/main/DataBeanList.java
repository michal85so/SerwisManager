package main;

import java.util.ArrayList;

public class DataBeanList {
  public ArrayList<DataBean> getDataBeanList() {
     ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();

     dataBeanList.add(produce("1", "Wymiana dysku", "01/09/2016", "10.30"));
     dataBeanList.add(produce("2", "Wymiana pamięci RAM","01/09/2016", "20.30"));
     dataBeanList.add(produce("3", "Naprawa monitora","01/09/2016", "30.30"));
     dataBeanList.add(produce("4", "Wymiana karty graficznej","01/09/2016", "40.30"));

     return dataBeanList;
  }

  private DataBean produce(String id, String name, String date, String price) {
     DataBean dataBean = new DataBean();
     dataBean.setId(id);
     dataBean.setName(name);
     dataBean.setDate(date);
     dataBean.setPrice(price);
     
     return dataBean;
  }
}
