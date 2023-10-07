package com.sky.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
   //判断当前加入购物车的商品是否已经存在了，如果存在只需数量加一，如果不存在则插入一条数据
        //创建实体类用于查询mapper
        ShoppingCart shoppingCart = new ShoppingCart();
        /*拷贝DTO*/
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();
        /*查询mapper*/
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        /*判断list*/
        if(list != null && list.size()>0){
             ShoppingCart cart = list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        }
        shoppingCartMapper.insert(shoppingCart);
    }

    @Override
    public void cleanShoppingCart() {
        Long user = BaseContext.getCurrentId();
        shoppingCartMapper.delete(user);
    }
}
