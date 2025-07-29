# 小窝服务器项目

## 项目架构

本项目采用Spring Boot多模块架构，结构清晰，职责分明。

### 模块结构

```
my-little-nest-server/
├── pom.xml                    # 父级POM，管理所有子模块
├── nest-common/               # 公共模块
│   ├── 配置类、工具类、常量
│   ├── 实体基类、通用异常处理
│   └── 公共注解、验证器
├── nest-web/                  # Web服务模块（端口：8080）
│   ├── 小窝主要业务功能
│   ├── 控制器、服务层、数据访问层
│   └── 定时任务、异步服务
└── nest-admin/                # 管理后台模块（端口：8089）
    ├── 后台管理功能
    ├── 用户权限管理
    └── 系统配置管理
```

### 技术栈

- **框架**: Spring Boot 2.7.8
- **数据库**: MySQL + MyBatis
- **安全**: Spring Security + JWT
- **工具**: Lombok、Hutool
- **分页**: PageHelper
- **加密**: Jasypt

### 模块依赖关系

```
nest-web    ←依赖→  nest-common
nest-admin  ←依赖→  nest-common
```

## 运行说明

### 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 5.7+

### 编译项目

```bash
# 编译所有模块
mvn clean compile

# 打包所有模块
mvn clean package
```

### 启动服务

#### 启动Web服务
```bash
cd nest-web
mvn spring-boot:run
```
访问地址: http://localhost:8080

#### 启动管理后台
```bash
cd nest-admin  
mvn spring-boot:run
```
访问地址: http://localhost:8089/admin

### 开发环境配置

1. **数据库配置**: 在各模块的 `application-dev.yml` 中配置数据库连接
2. **多环境支持**: 支持dev、test、prod三个环境
3. **日志配置**: 使用logback，配置文件在各模块的resources目录下

### 模块说明

#### nest-common 公共模块
- 提供公共的配置、工具类、常量定义
- 包含基础实体类、异常处理、缓存配置
- 其他模块的基础依赖

#### nest-web Web服务模块  
- 主要的业务功能实现
- 包含文章管理、用户服务、定时任务等
- 对外提供REST API接口

#### nest-admin 管理后台模块
- 后台管理功能
- 用户权限管理、系统配置
- 集成Spring Security安全框架

## 架构优势

1. **模块化**: 各模块职责清晰，便于维护和扩展
2. **解耦合**: 通过公共模块实现代码复用，减少重复
3. **可扩展**: 易于添加新的业务模块
4. **独立部署**: 各模块可以独立打包和部署
5. **统一管理**: 父POM统一管理依赖版本和插件配置

## 开发规范

### 包结构规范
```
fun.dfwh.{module}/
├── controller/     # 控制器层
├── service/        # 业务逻辑层  
│   └── impl/       # 服务实现
├── mapper/         # 数据访问层
├── entity/         # 实体类
├── dto/            # 数据传输对象
├── vo/             # 视图对象
├── config/         # 配置类
└── constant/       # 常量定义
```

### 分层说明
- **Controller层**: 接收HTTP请求，参数校验，调用Service
- **Service层**: 业务逻辑处理，事务控制
- **Mapper层**: 数据访问，与数据库交互
- **Entity层**: 数据库实体映射
- **DTO层**: 数据传输对象，用于模块间数据传递
- **VO层**: 视图对象，用于前端展示

## 部署说明

### 开发环境
```bash
mvn clean package -Pdev
java -jar nest-web/target/nest-web-1.0-SNAPSHOT.jar
java -jar nest-admin/target/nest-admin-1.0-SNAPSHOT.jar
```

### 生产环境
```bash
mvn clean package -Pprod
# 部署到服务器运行
``` 