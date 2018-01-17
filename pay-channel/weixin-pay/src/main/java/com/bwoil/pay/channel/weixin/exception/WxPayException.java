package com.bwoil.pay.channel.weixin.exception;


import com.bwoil.pay.channel.weixin.constants.WxPayErrorCode;

/**
 * 支付错误
 * Created by daoxing on 2016/9/23.
 */
 public class WxPayException extends RuntimeException
    {
        // FIELDS
        private WxPayErrorCode errorCode;

        // CONSTRUCT
        public WxPayException()
        {
            return;
        }

        public WxPayException(Throwable cause)
        {
            super.initCause(cause);

        }

        public WxPayException(WxPayErrorCode aErrorCode, Throwable cause)
        {
            this.errorCode = aErrorCode;
            super.initCause(cause);

            return;
        }

        public WxPayException(WxPayErrorCode aErrorCode)
        {
            this(aErrorCode, null);
        }

        public WxPayException(String aErrorName)
        {
            this(
                    WxPayErrorCode.forName(aErrorName),
                    null
            );
        }

        public WxPayException(String aErrorName, Throwable cause)
        {
            this(
                    WxPayErrorCode.forName(aErrorName),
                    cause
            );
        }

        public WxPayException(int aErrorCode)
        {
            this(
                    WxPayErrorCode.forCode(aErrorCode),
                    null
            );
        }

        public WxPayException(int aErrorCode, Throwable cause)
        {
            this(
                    WxPayErrorCode.forCode(aErrorCode),
                    cause
            );
        }

        // EXCEPTION
        @Override
        public String getMessage()
        {
            if (this.errorCode == null)
                return null;
            // else
            return this.errorCode.toString()+':'+this.errorCode.getMsg();
        }
}
