package com.example.webfluxjwt.models.reqrespmodel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReqRespModel<T> implements IReqRespModel<T> {
        private T data;
        private String message;

        public T getData() {
                return this.data;
        }

        @Override
        public String getMessage() {
                return message;
        }
}
