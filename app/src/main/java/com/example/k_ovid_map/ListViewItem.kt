package com.example.k_ovid_map

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-09-15
 * @desc
 */
// ListViewItem.kt
class ListViewItem {

    private var center: String? = null
    private var centeraddr: String? = null

    constructor(center: String, centeraddr: String){
        this.center = center
        this.centeraddr = centeraddr
    }

    fun setcenter(storeName: String) {
        this.center = storeName
    }

    fun setcenteraddr(remain_stat: String) {
        this.centeraddr = remain_stat
    }

    fun getcenter(): String? {
        return this.center
    }

    fun getcenteraddr(): String? {
        return this.centeraddr
    }
}