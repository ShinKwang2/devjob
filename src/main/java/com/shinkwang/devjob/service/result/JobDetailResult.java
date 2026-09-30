package com.shinkwang.devjob.service.result;

import com.shinkwang.devjob.dto.JobResponse;

public record JobDetailResult(
        JobResponse response,
        long jobVersion,
        long companyVersion
) {
    public String validator() {
        return "job-%d-j%d-c%d"
                .formatted(response.id(), jobVersion, companyVersion);
    }
}
