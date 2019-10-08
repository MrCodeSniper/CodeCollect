package com.example.myapplication;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil<K,V> {



    private ReferenceQueue<V> referenceQueue;

    private ConcurrentHashMap<K,InnerSoftReference<V>> cacheMap;

    public CacheUtil() {
        cacheMap=new ConcurrentHashMap<>();
        referenceQueue=new ReferenceQueue<>();
    }



    public void clearCollectedReference(){
        InnerSoftReference innerSoftReference;
        while ((innerSoftReference=(InnerSoftReference<V>)referenceQueue.poll())!=null){
            //如果有对象已经被回收根据回收引用的KEY 从map中删除
            cacheMap.remove(innerSoftReference.getKey());
        }
    }


    public V get(K key){
        synchronized (CacheUtil.class){
            InnerSoftReference<V> ref=cacheMap.get(key);
            if(ref!=null){
                return (V) ref.get();
            }
        }
        return null;
    }

    public void put(K key,V value){
        cacheMap.put(key,new InnerSoftReference<V>(key,value,referenceQueue));
        clearCollectedReference();
    }




    public int getSize(){
        return cacheMap.size();
    }

    public boolean isContain(K k){
        return cacheMap.contains(k);
    }


    public void clear(){
        cacheMap.clear();;
        clearCollectedReference();
    }


    private class InnerSoftReference<V> extends SoftReference{

        private K key;

        public InnerSoftReference(K key,V v, ReferenceQueue q) {
            super(v,q);
            this.key=key;
        }

        public K getKey() {
            return key;
        }
    }

}
