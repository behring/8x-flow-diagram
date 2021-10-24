# 服务与业务能力
## 业务能力表 - 商品销售上下文
| 角色 | HTTP方法 | URI | 业务能力 | 业务能力服务 |
| --- | --- | --- | --- | --- |
| 买家 | POST | /order-requests | 发起询问商品价格 | 商品销售上下文服务 |
|  | GET | /order-requests | 查看询问商品价格列表 | 商品销售上下文服务 |
| 买家 | GET | /order-requests/{rid} | 查看询问商品价格 | 商品销售上下文服务 |
| 买家 | PUT | /order-requests/{rid} | 更改询问商品价格 | 商品销售上下文服务 |
| 买家 | DELETE | /order-requests/{rid} | 取消询问商品价格 | 商品销售上下文服务 |



