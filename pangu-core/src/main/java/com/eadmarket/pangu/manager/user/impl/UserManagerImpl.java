package com.eadmarket.pangu.manager.user.impl;

import com.google.common.base.Preconditions;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.domain.UserDO.UserStatus;
import com.eadmarket.pangu.manager.user.UserManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Setter;

/**
 * UserManager实现
 *
 * @author liuyongpo@gmail.com
 */
class UserManagerImpl implements UserManager {

  private final static Logger LOG = LoggerFactory.getLogger(UserManagerImpl.class);

  @Setter
  private UserDao userDao;

  @Override
  public Long registerUser(UserDO user) throws ManagerException {
    UserDO userInDb = getUserByEmail(user.getEmail());
    if (userInDb != null) {
      throw new ManagerException(ExceptionCode.USER_REGISTED);
    }
    String password = user.getPassword();
    String encryptedPwd = encryptPassword(password);
    user.setPassword(encryptedPwd);
    Long id;
    try {
      id = userDao.insert(user);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
    }
    try {
      sendActiveEmailTo(id);
    } catch (Exception ex) {
      LOG.error("send email to " + id, ex);
    }
    return id;
  }

  @Override
  public UserDO getUserById(Long userId) throws ManagerException {
    Preconditions.checkArgument(userId != null && userId > 0, "email can't be blank");

    try {
      return userDao.getById(userId);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "userId:" + userId, ex);
    }
  }

  @Override
  public UserDO getUserByEmailAndPwd(String email, String password)
      throws ManagerException {
    try {

      UserDO user = userDao.getByEmail(email);
      if (user == null) {
        throw new ManagerException(ExceptionCode.USER_NOT_EXIST);
      }

      boolean isPassMatch = BCrypt.checkpw(password, user.getPassword());
      if (!isPassMatch) {
        throw new ManagerException(ExceptionCode.PWD_NOT_MATCH);
      }
      return user;
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
    }
  }

  @Override
  public UserDO getUserByEmail(String email) throws ManagerException {

    Preconditions.checkArgument(StringUtils.isNotBlank(email), "email can't be blank");

    try {
      return userDao.getByEmail(email);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "email:" + email, ex);
    }
  }

  @Override
  public void activeUser(Long userId) throws ManagerException {
    Preconditions.checkArgument(userId != null && userId > 0, "email can't be blank");

    try {

      UserDO user = userDao.getById(userId);
      if (user == null) {
        throw new IllegalArgumentException("can't find such User with this userId:" + userId);
      }

      if (user.isActive()) {
        return;
      }

      userDao.updateStatusById(userId, UserStatus.ACTIVE);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "userId:" + userId, ex);
    }
  }

  @Override
  public void sendActiveEmailTo(Long userId) throws ManagerException {
    try {

      UserDO user = userDao.getById(userId);
      if (user == null) {
        throw new IllegalArgumentException("can't find such User with this userId:" + userId);
      }

      if (user.isActive()) {
        return;
      }
      //TODO()
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "userId:" + userId, ex);
    }
  }

  @Override
  public void resetPassword(Long userId, String orinalPassword,
                            String newPassword) throws ManagerException {
    try {
      UserDO user = userDao.getById(userId);
      if (user == null) {
        throw new ManagerException(ExceptionCode.USER_NOT_EXIST);
      }
      boolean isPassMatch = BCrypt.checkpw(orinalPassword, user.getPassword());
      if (!isPassMatch) {
        throw new ManagerException(ExceptionCode.PWD_NOT_MATCH);
      }
      userDao.updatePassword(userId, encryptPassword(newPassword));
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
    }
  }

  @Override
  public void updateProfile(UserDO userDO) throws ManagerException {
    // TODO Auto-generated method stub

  }

  /**
   * 使用BCrypt加密算法对密码加密
   *
   * @param orginalPassword 原始明文密码
   * @return 加密之后的密码
   */
  private static String encryptPassword(String orginalPassword) {
    return BCrypt.hashpw(orginalPassword, BCrypt.gensalt(12));
  }

}
