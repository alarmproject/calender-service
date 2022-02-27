package io.my.calender.base.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseExtentionResponse<T> extends BaseResponse{
    T returnValue;
}
