package com.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
//数据库回滚
@Transactional
@Rollback(value = false)
public class SeckillServiceTest {

    private final Logger logger = Logger.getLogger(getClass());


    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() {

        List<Seckill> list = seckillService.getSeckillList();
        System.out.println(list);
    }

    @Test
    public void testGetById() {
        long seckillId = 1000L;
        Seckill seckill = seckillService.getById(seckillId);
        System.out.println(seckill);
    }

    @Test
    @Rollback(value = true)
    public void testSeckillLogic() {

        long id = 2L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExpose()) {
            logger.info("exposer = " + exposer);
            long phone = 13057617803L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info(seckillExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            logger.warn("exposer = " + exposer);
        }
    }

    @Test
    public void executeSeckillProcedure() {
        long seckillId = 2L;
        long phone = 13333333333L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExpose()) {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(seckillExecution.getStateInfo());
        }
    }
}
