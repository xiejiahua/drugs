
package com.example.develop2.drugs.contract;

public interface BaseView<T> {

    /**
     * 给View绑定Presenter
     * @param presenter
     */
    void setPresenter(T presenter);

}
