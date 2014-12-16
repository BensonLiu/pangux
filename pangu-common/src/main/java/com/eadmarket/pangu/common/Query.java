package com.eadmarket.pangu.common;

import lombok.Getter;
import lombok.Setter;


/**
 * 通用的query对象
 *
 * @author liuyongpo@gmail.com
 */
public class Query<T> {

  private static final Integer defaultPageSize = new Integer(20);
  private static final Integer defaultFristPage = new Integer(1);
  private static final Integer defaultTotleItem = new Integer(0);

  @Setter
  @Getter
  private T condition;

  private Integer pageSize = defaultPageSize;

  private Integer totalItem;

  private Integer currentPage;

  private int startRow;
  private int endRow;

  @Setter
  @Getter
  private String orderType;

  @Setter
  @Getter
  private String orderBy;

  public static <T> Query<T> create(T data) {
    Query<T> query = new Query<T>();
    query.setCondition(data);
    return query;
  }

  public boolean isFirstPage() {
    return this.getCurrentPage().intValue() == 1;
  }

  /**
   * @return Returns the currentPage.
   */
  public Integer getCurrentPage() {
    if (currentPage == null) {
      return defaultFristPage;
    }

    return currentPage;
  }

  /**
   * @param currentPage The currentPage to set.
   */
  public void setCurrentPage(Integer cPage) {
    if ((cPage == null) || (cPage.intValue() <= 0)) {
      this.currentPage = null;
    } else {
      this.currentPage = cPage;
    }
    setStartEndRow();
  }

  public int getPreviousPage() {
    int back = this.getCurrentPage().intValue() - 1;

    if (back <= 0) {
      back = 1;
    }

    return back;
  }

  public boolean isLastPage() {
    return this.getTotalPage() == this.getCurrentPage().intValue();
  }

  public int getTotalPage() {
    int pgSize = this.getPageSize().intValue();
    int total = this.getTotalItem().intValue();
    int result = total / pgSize;

    if ((total == 0) || ((total % pgSize) != 0)) {
      result++;
    }

    return result;
  }

  /**
   * @return Returns the pageSize.
   */
  public Integer getPageSize() {
    if (pageSize == null) {
      return getDefaultPageSize();
    }

    return pageSize;
  }

  /**
   * @param pageSize The pageSize to set.
   */
  public void setPageSize(Integer pSize) {
    if ((pSize == null) || (pSize.intValue() <= 0)) {
      this.pageSize = null;
    } else {
      this.pageSize = pSize;
    }
    setStartEndRow();
  }

  /**
   * @return Returns the totalItem.
   */
  public Integer getTotalItem() {
    if (totalItem == null) {
      return defaultTotleItem;
    }

    return totalItem;
  }

  protected Integer getDefaultPageSize() {
    return defaultPageSize;
  }

  public void setTotalItem(Integer tItem) {
    if (tItem == null) {
      throw new IllegalArgumentException("TotalItem can't be null.");
    }

    this.totalItem = tItem;

    int current = this.getCurrentPage().intValue();
    int lastPage = this.getTotalPage();

    if (current > lastPage) {
      this.setCurrentPage(new Integer(lastPage));
    }
  }

  public int getNextPage() {
    int back = this.getCurrentPage().intValue() + 1;

    if (back > this.getTotalPage()) {
      back = this.getTotalPage();
    }

    return back;
  }

  public void setCurrentPageString(String pageString) {
    if (isBlankString(pageString)) {
      this.setCurrentPage(defaultFristPage);
    }

    try {
      Integer integer = new Integer(pageString);

      this.setCurrentPage(integer);
    } catch (NumberFormatException ignore) {
      this.setCurrentPage(defaultFristPage);
    }
  }

  /**
   * @param pageSizeString
   *
   * @return
   */
  private boolean isBlankString(String pageSizeString) {
    if (pageSizeString == null) {
      return true;
    }

    if (pageSizeString.trim().length() == 0) {
      return true;
    }

    return false;
  }

  private void setStartEndRow() {
    this.startRow = this.getPageSize().intValue() * (this.getCurrentPage().intValue() - 1) + 1;
    this.endRow = this.startRow + this.getPageSize().intValue() - 1;
  }

  public boolean hasSetPageSize() {
    return pageSize != null;
  }

  public void setPageSizeString(String pageSizeString) {
    if (isBlankString(pageSizeString)) {
      return;
    }

    try {
      Integer integer = new Integer(pageSizeString);

      this.setPageSize(integer);
    } catch (NumberFormatException ignore) {
    }
  }

  public int getMysqlPageFirstItem() {
    return getPageFirstItem() - 1;
  }

  public int getPageFirstItem() {
    int cPage = this.getCurrentPage().intValue();

    if (cPage == 1) {
      return 1; // 第一页开始当然是第 1 条记录
    }

    cPage--;

    int pgSize = this.getPageSize().intValue();

    return (pgSize * cPage) + 1;
  }

  public int getPageLastItem() {
    int cPage = this.getCurrentPage().intValue();
    int pgSize = this.getPageSize().intValue();
    int assumeLast = pgSize * cPage;
    int totalItem = getTotalItem().intValue();

    if (assumeLast > totalItem) {
      return totalItem;
    } else {
      return assumeLast;
    }
  }

  /**
   * @return Returns the endRow.
   */
  public int getEndRow() {
    return endRow;
  }

  /**
   * @param endRow The endRow to set.
   */
  public void setEndRow(int endRow) {
    this.endRow = endRow;
  }

  /**
   * @return Returns the startRow.
   */
  public int getStartRow() {
    return startRow;
  }

  /**
   * @param startRow The startRow to set.
   */
  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }

}
