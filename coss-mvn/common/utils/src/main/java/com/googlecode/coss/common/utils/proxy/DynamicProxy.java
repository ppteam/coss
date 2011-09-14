package com.googlecode.coss.common.utils.proxy;

/**
 * 动态代理
 */
public class DynamicProxy extends BaseDynamicProxy {

	@Override
	protected void doBefore() {
		System.out.println("============ 进行校验 =============");
	}

	@Override
	protected Object doAfter(Object res) {
		System.out.println("============ 记录日志 =============");
		String endRes = String.valueOf(res) + " do more.";
		return endRes;
	}

}
