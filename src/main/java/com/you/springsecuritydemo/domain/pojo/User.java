package com.you.springsecuritydemo.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: User实体类
 * @author: D
 * @create: 2020-06-24 9:27
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable {
    private static final long serialVersionUID = 2646549708067524760L;
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String headimgurl;

    private String phone;

    private String telephone;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private Boolean sex;

    private Integer status;

    private Date createtime;

    private Date updatetime;

    public interface Status {
        int DISABLED = 0;
        int VALID = 1;
        int LOCKED = 2;
    }
}