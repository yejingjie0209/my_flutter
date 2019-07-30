
@file:Suppress("NOTHING_TO_INLINE")

package com.love.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * RxJAVA Observable 垃圾回收,单利模式，方便调用和应用层统一调用
 */
@Suppress("unused")
class DisposableBag private constructor(){

    private val map = mutableMapOf<String,CompositeDisposable>()

    companion object {
        @JvmField
        var instance = DisposableBag()
    }

    fun add(disposable: Disposable, tag: String){
        val bag = map.getOrElse(tag){
            CompositeDisposable()
        }
        bag.add(disposable)
        map[tag] = bag
    }

    fun clear(tag: String) {
        map[tag]?.clear()
        map.remove(tag)
    }

    fun size(tag: String) = map[tag]?.size() ?: 0

    fun clearAll(){
         map.forEach { entry ->
             entry.value.clear()
         }
        map.clear()
    }
}

@Suppress("unused")
inline fun Disposable.addToBag(tag: String){
    DisposableBag.instance.add(this, tag)
}