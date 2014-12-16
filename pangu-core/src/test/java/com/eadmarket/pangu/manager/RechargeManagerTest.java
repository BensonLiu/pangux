package com.eadmarket.pangu.manager;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.manager.recharge.RechargeManager;
import com.eadmarket.pangu.manager.user.UserManager;

import org.junit.Test;

import java.util.Date;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author liuyongpo@gmail.com
 */
public class RechargeManagerTest extends BaseTest {

  @Resource
  UserManager userManager;

  @Resource
  RechargeManager rechargeManager;

  @Test
  public void testFinish() throws ManagerException {

    UserDO user = userManager.getUserById(10L);
    Long originalAccountBalance = user.getBalance();

    long outOrderId = new Date().getTime();
    String outOrderIdStr = String.valueOf(outOrderId);
    long cashToAdd = 100L;
    boolean finish = rechargeManager.finish(2L, outOrderIdStr, cashToAdd);

    assertThat(finish, is(true));

    user = userManager.getUserById(10L);
    Long currentAccountBalance = user.getBalance();

    assertThat(originalAccountBalance + cashToAdd, is(equalTo(currentAccountBalance)));
  }
}
