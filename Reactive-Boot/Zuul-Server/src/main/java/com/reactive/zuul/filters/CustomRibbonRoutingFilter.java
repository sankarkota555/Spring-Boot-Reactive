package com.reactive.zuul.filters;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class CustomRibbonRoutingFilter extends ZuulFilter {

	private static final Logger log = LoggerFactory.getLogger(CustomRibbonRoutingFilter.class);

	private static final String ROUTE_TYPE = "route";

	private static final String SERVICE_ID_KEY = "serviceId";

	@Autowired
	private RibbonCommandFactory<?> ribbonCommandFactory;

	private RibbonRoutingFilter ribbonRoutingFilter;

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		log.info("ctx.getRouteHost():{}, ctx.get(SERVICE_ID_KEY):{}, ctx.sendZuulResponse():{}", ctx.getRouteHost(),
				ctx.get(SERVICE_ID_KEY), ctx.sendZuulResponse());
		return (ctx.getRouteHost() == null && ctx.get(SERVICE_ID_KEY) != null && ctx.sendZuulResponse());
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext currentRequestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = currentRequestContext.getRequest();
		log.info("Processing request:{}", request.getRequestURI());
		return ribbonRoutingFilter.run();
	}

	@Override
	public String filterType() {
		return ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@PostConstruct
	public void initRibbonFilter() {
		ribbonRoutingFilter = new RibbonRoutingFilter(new ProxyRequestHelper(new ZuulProperties()),
				ribbonCommandFactory, new ArrayList<>());
	}

}
