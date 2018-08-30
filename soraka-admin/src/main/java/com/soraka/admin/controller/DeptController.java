package com.soraka.admin.controller;

import com.soraka.admin.model.domain.DeptDO;
import com.soraka.admin.model.dto.Page;
import com.soraka.admin.model.dto.QueryParam;
import com.soraka.admin.model.dto.R;
import com.soraka.admin.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yongjie.teng
 * @date 2018/8/17
 * @package com.soraka.admin.controller
 */
@Api(tags = "部门服务")
@RequestMapping("dept")
@RestController
public class DeptController extends BaseController {
    @Autowired
    DeptService deptService;

    @GetMapping("{id}")
    @ApiOperation("获取部门详情")
    public R get(@PathVariable("id") Long id) {
        DeptDO dept = deptService.get(id);
        R r = R.success();
        r.setData(dept);
        return r;
    }

    @GetMapping
    @ApiOperation("查询部门列表")
    public R find(QueryParam queryParam) {
        Page page = deptService.findPage(queryParam);
        R r = R.success();
        r.setData(page);
        return r;
    }

    @PostMapping
    @ApiOperation("添加部门")
    public R save(@Valid @RequestBody DeptDO deptDO) {
        return R.operate(deptService.save(deptDO));
    }

    @PutMapping
    @ApiOperation("更新部门信息")
    public R update(@RequestBody DeptDO deptDO) {
        return R.operate(deptService.update(deptDO));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除部门", notes = "根据部门ID删除")
    public R delete(@PathVariable("id") Long id) {
        return R.operate(deptService.delete(id));
    }
}
