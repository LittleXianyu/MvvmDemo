package com.example.mvvmdemo.jetpack.databinding.model

import androidx.databinding.ObservableField

class Model1 {
    /**
     * ObservableField 装箱类，对属性包装@Bindable功能，属性改变自动更新UI
     */
    var name: ObservableField<String>
    constructor(name1: String) {
        name = ObservableField<String>(name1)
    }
}
