-- 库存写入缓存脚本
-- 作用：同时设置库存Hash以及过期时间，减少TCP次数
-- KEYS：hashKey
-- ARGV[1]：库存的过期时间（秒）
-- ARGV[2..n]：每个hash都共有3组属性，分别对应6个参数

-- 失效时间（秒）
local seconds = ARGV[1];

-- 可用库存
local availableField;
local availableValue;

local start = 2

for i = 1, #KEYS do
    local hashKey = KEYS[i];
    availableField = ARGV[start];
    availableValue = ARGV[start + 1];
    start = start + 2;
    redis.call('hset', hashKey, availableField, availableValue);
    redis.call('expire', hashKey, seconds);
end

return true;