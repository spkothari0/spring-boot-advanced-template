package com.shreyas.spring_boot_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/cache", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('ADMIN')")
public class CacheController extends BaseController {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CacheController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/")
    @Operation(summary = "Get all cache", description = "Get complete data in cache in map structure")
    public ResponseEntity<APIResponse<Map<String, String>>> getAllCacheData(){
        Map<String, String> allData = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                String value = redisTemplate.opsForValue().get(key);
                allData.put(key, value);
            }
        }
        return SuccessResponse(allData);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by id", description = "Get course by id")
    public ResponseEntity<APIResponse<String>> getCacheFromKey(@PathVariable String id){
        String cacheValue = redisTemplate.opsForValue().get(id);
        if(cacheValue != null && cacheValue.isEmpty())
            return SuccessResponse(cacheValue);
        else
            return ErrorResponse("Couldn't find data in cache'");
    }

    @PostMapping("/")
    @Operation(summary = "Put data into cache", description = "Store data in cache with specified key")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> putData(@RequestParam String key, @RequestBody String value) {
        redisTemplate.opsForValue().set(key, value);
        return SuccessResponseMessage("Data stored successfully");
    }

    @DeleteMapping("/{key}")
    @Operation(summary = "Remove data from cache", description = "Remove data from cache using specified key")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse<Boolean>> removeData(@PathVariable String key) {
        Boolean deletedCount = redisTemplate.delete(key);
        if(Boolean.TRUE.equals(deletedCount))
            return SuccessResponse(true);
        else
            return ErrorResponse("Couldn't remove data from cache");
    }

    @DeleteMapping("/")
    @Operation(summary = "Remove all data from cache", description = "Remove all data from cache")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse<Long>> removeAllData() {
        long deletedCount = redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*")));
        if(deletedCount>0)
            return SuccessResponse(deletedCount);
        else
            return ErrorResponse("Couldn't delete all data from cache");
    }
}
