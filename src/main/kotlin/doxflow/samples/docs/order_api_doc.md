# 服务与业务能力
## 业务能力表 - 商品销售上下文
| 角色 | HTTP方法 | URI | 业务能力 | 业务能力服务 |
| --- | --- | --- | --- | --- |
| 买家 | POST | /order-request | 创建询问商品价格 | 商品销售上下文服务 |
| 买家 | GET | /order-request | 查看询问商品价格 | 商品销售上下文服务 |
| 买家 | PUT | /order-request | 更改询问商品价格 | 商品销售上下文服务 |
| 买家 | DELETE | /order-request | 取消询问商品价格 | 商品销售上下文服务 |
| 卖家 | POST | /order-request/proposals | 创建商品报价方案 | 商品销售上下文服务 |
|  | GET | /order-request/proposals | 查看商品报价方案列表 | 商品销售上下文服务 |
| 卖家 | GET | /order-request/proposals/{pid} | 查看商品报价方案 | 商品销售上下文服务 |
| 卖家 | PUT | /order-request/proposals/{pid} | 更改商品报价方案 | 商品销售上下文服务 |
| 卖家 | DELETE | /order-request/proposals/{pid} | 取消商品报价方案 | 商品销售上下文服务 |
|  | POST | /orders | 创建商品订单合同 | 商品销售上下文服务 |
|  | GET | /orders | 查看商品订单合同列表 | 商品销售上下文服务 |
|  | GET | /orders/{oid} | 查看商品订单合同 | 商品销售上下文服务 |
|  | PUT | /orders/{oid} | 更改商品订单合同 | 商品销售上下文服务 |
|  | DELETE | /orders/{oid} | 取消商品订单合同 | 商品销售上下文服务 |
| 卖家 | POST | /orders/{oid}/payment | 创建订单支付请求 | 商品销售上下文服务 |
| 卖家 | GET | /orders/{oid}/payment | 查看订单支付请求 | 商品销售上下文服务 |
| 卖家 | PUT | /orders/{oid}/payment | 更改订单支付请求 | 商品销售上下文服务 |
| 卖家 | DELETE | /orders/{oid}/payment | 取消订单支付请求 | 商品销售上下文服务 |



