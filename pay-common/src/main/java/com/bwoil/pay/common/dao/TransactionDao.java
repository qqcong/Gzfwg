package com.bwoil.pay.common.dao;

import com.bwoil.common.framework.data.BaseDao;
import com.bwoil.pay.common.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDao extends BaseDao<Transaction,String> {
}
