package com.me.practicecode.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017/7/18.
 */
@Module
public class PersonModule {


    @Provides
    Person providesPerson(){
        return new Person();
    }

}
