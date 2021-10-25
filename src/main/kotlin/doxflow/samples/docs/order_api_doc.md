# 服务与业务能力
## 业务能力表 - 商品销售上下文
| 角色 | HTTP方法 | URI | 业务能力 | 业务能力服务 |
| --- | --- | --- | --- | --- |
| 买家 | GET | /order-request | 查看询问商品价格 | 商品销售上下文服务 |
| 买家 | PUT | /order-request | 更改询问商品价格 | 商品销售上下文服务 |
| 买家 | DELETE | /order-request | 取消询问商品价格 | 商品销售上下文服务 |
| 卖家 | POST | /order-request/proposals | 发起商品报价方案 | 商品销售上下文服务 |
|  | GET | /order-request/proposals | 查看商品报价方案列表 | 商品销售上下文服务 |
| 卖家 | GET | /order-request/proposals/{pid} | 查看商品报价方案 | 商品销售上下文服务 |
| 卖家 | PUT | /order-request/proposals/{pid} | 更改商品报价方案 | 商品销售上下文服务 |
| 卖家 | DELETE | /order-request/proposals/{pid} | 取消商品报价方案 | 商品销售上下文服务 |
|  | GET | /order | 查看商品订单合同 | 商品销售上下文服务 |
|  | PUT | /order | 更改商品订单合同 | 商品销售上下文服务 |
|  | DELETE | /order | 取消商品订单合同 | 商品销售上下文服务 |



