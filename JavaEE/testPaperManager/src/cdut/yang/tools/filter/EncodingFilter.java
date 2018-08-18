package cdut.yang.tools.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {  
    
    private String charset;  
    @Override  
    public void destroy() {  
        // TODO Auto-generated method stub  
    }
	@Override
	public void init(FilterConfig config) throws ServletException {  
        //��web.xml�е�filter��������Ϣ��ȡ���ַ���  
        this.charset = config.getInitParameter("charset");  
    }  
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        //��init����ȡ�õ�charset���Ǳ�����������request�����charset  
        request.setCharacterEncoding(this.charset);  
        //�������ƽ�����һ�¹�������������е�����¡�  
        chain.doFilter(request, response);  
    }  
  
    
	
}  