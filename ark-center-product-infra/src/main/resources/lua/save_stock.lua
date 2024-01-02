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


-- 已锁定库存
local lockedField;
local lockedValue;

-- 已售库存
local soldField;
local soldValue;

local start = 2

for i = 1, #KEYS do
    local hashKey = KEYS[i];
    availableField = string.sub(ARGV[start], 2, -2);
    availableValue = ARGV[start + 1];
    lockedField = string.sub(ARGV[start + 2], 2, -2);
    lockedValue = ARGV[start + 3];
    soldField = string.sub(ARGV[start + 4], 2, -2);
    soldValue = ARGV[start + 5];
    start = start + 6;
    redis.call('hmset', hashKey, availableField, availableValue, lockedField, lockedValue, soldField, soldValue);
    redis.call('expire', hashKey, seconds);
end

return true;