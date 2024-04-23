package com.base;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Service基本类接口
 * @param <E>
 */
public interface IServiceBase<E> {
    public List<E> select();/**查询当前对象信息列表**/
    public List<E> select(E y);
    public E find(Object id);/**通过id查询具体对象**/
    public E findEntity(E id);
    public List<E> selectPage(E obj, int page, int pageSize);
    public List<E> selectPageExample(Example obj , int page , int pageSize);
    public int delete(Object id);/**删除单个对象**/
    public int insert(E y);/**插入单个对象**/
    public int update(E y);/**更新单个对象信息**/
}
