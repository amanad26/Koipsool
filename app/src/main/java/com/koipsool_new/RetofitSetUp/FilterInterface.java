package com.koipsool_new.RetofitSetUp;

public interface FilterInterface {

    void select_brand(String brand_id, String brand_name);
    void unselect_brand(String brand_name );


    void selected_model(String brandName, String  model_name);
    void un_selected_model(String brandName, String  model_name);

    void selected_category(String category_name);
    void un_selected_category(String category_name);

}
