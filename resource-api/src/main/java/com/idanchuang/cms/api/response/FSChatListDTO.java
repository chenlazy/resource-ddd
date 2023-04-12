package com.idanchuang.cms.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-09 10:36
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FSChatListDTO implements Serializable {

    private static final long serialVersionUID = 6047594404787324968L;

    /**
     * 群名称
     */
    private String chatName;

    /**
     * 群id
     */
    private String chatId;
}
