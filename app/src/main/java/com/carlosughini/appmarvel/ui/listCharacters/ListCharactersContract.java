package com.carlosughini.appmarvel.ui.listCharacters;

public interface ListCharactersContract {


    interface View {

        void initView();

        void showProgress();

        void hideProgress();

        void displayList();

    }

    interface Presenter {



    }

}
