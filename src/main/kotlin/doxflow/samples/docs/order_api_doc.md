# 服务与业务能力
## 业务能力表 - 商品销售上下文
| 角色 | HTTP方法 | URI | 业务能力 | 业务能力服务 |
| --- | --- | --- | --- | --- |
| 买家 | POST | /order-requests | 发起询问商品价格 | 商品销售上下文服务 |
|  | GET | /order-requests | 查看询问商品价格列表 | 商品销售上下文服务 |
| 买家 | GET | /order-requests/{oid} | 查看询问商品价格 | 商品销售上下文服务 |
| 买家 | PUT | /order-requests/{oid} | 更改询问商品价格 | 商品销售上下文服务 |
| 买家 | DELETE | /order-requests/{oid} | 取消询问商品价格 | 商品销售上下文服务 |
| 卖家 | POST | /order-requests/{oid}/proposals | 发起商品报价方案 | 商品销售上下文服务 |
|  | GET | /order-requests/{oid}/proposals | 查看商品报价方案列表 | 商品销售上下文服务 |
| 卖家 | GET | /order-requests/{oid}/proposals/{pid} | 查看商品报价方案 | 商品销售上下文服务 |
| 卖家 | PUT | /order-requests/{oid}/proposals/{pid} | 更改商品报价方案 | 商品销售上下文服务 |
| 卖家 | DELETE | /order-requests/{oid}/proposals/{pid} | 取消商品报价方案 | 商品销售上下文服务 |
|  | POST | /orders | 发起商品订单合同 | 商品销售上下文服务 |
|  | GET | /orders | 查看商品订单合同列表 | 商品销售上下文服务 |
|  | GET | /orders/{oid} | 查看商品订单合同 | 商品销售上下文服务 |
|  | PUT | /orders/{oid} | 更改商品订单合同 | 商品销售上下文服务 |
|  | DELETE | /orders/{oid} | 取消商品订单合同 | 商品销售上下文服务 |



