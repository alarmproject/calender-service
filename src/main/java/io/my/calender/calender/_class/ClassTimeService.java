package io.my.calender.calender._class;

import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.ClassTimeRepository;
import io.my.calender.calender._class.payload.request.ModifyClassTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClassTimeService {
    private final ClassTimeRepository classTimeRepository;

    public Mono<BaseResponse> modifyClassTime(ModifyClassTime requestBody) {
        return this.classTimeRepository.findById(requestBody.getId())
                .flatMap(entity -> {
                    entity.setStartTime(requestBody.getStartTime());
                    entity.setEndTime(requestBody.getEndTime());
                    entity.setDay(requestBody.getDay());
                    return this.classTimeRepository.save(entity);
                })
                .map(entity -> new BaseResponse());
    }

    public Mono<BaseResponse> removeClassTime(Long id) {
        return this.classTimeRepository.deleteById(id).map(o -> new BaseResponse());
    }
}
