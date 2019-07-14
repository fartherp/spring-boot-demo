/**
 *    Copyright (c) 2018-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.demo.common.req;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/8/30
 */
public class PageReq {
    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 页大小
     */
    private Integer pageSize = 10;

    private Page pager;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 上层控制使用分页
     */
    public void buildPage() {
        pager = PageHelper.startPage(this.pageNo, this.pageSize);
    }

    public int getTotal() {
        if (pager != null) {
            return (int) pager.getTotal();
        }
        return 0;
    }
}
