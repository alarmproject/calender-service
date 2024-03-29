package io.my.calender.base.context;

import org.springframework.web.server.ServerWebExchange;

import java.io.Serializable;

final public class JwtContext implements Serializable {
    private final String jwt;
    private Object user;
    private Long userId;

    public JwtContext(final ServerWebExchange exchange) {
        Object authorization = exchange.getRequest().getHeaders().get("Authorization");
        if (authorization != null) {
            jwt = authorization.toString().substring(8).replace("]", "");
        } else { jwt = null; }
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return this.jwt;
    }

    public Object getUser () {
        return this.user;
    }

    public Long getUserId() {
        return this.userId;
    }
}
