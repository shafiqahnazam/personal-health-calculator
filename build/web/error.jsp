<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${not empty param.title ? param.title : 'Application Error'}</title>
    <link href='https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700&family=Roboto:wght@400;500&display=swap' rel='stylesheet'>
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f1e2d1; color: #541212; margin: 0; padding: 25px; display: flex; align-items: center; justify-content: center; min-height: 100vh; box-sizing: border-box; }
        .error-dashboard-card { max-width: 550px; width: 100%; background: #ffffff; border-radius: 16px; padding: 40px; box-shadow: 0 10px 30px rgba(84,18,18,0.15); border-top: 8px solid #c21c1c; text-align: center; box-sizing: border-box; animation: cardSlideUp 0.4s ease-out; }
        .chili-alert-badge { width: 70px; height: 70px; background-color: #fbebe8; border: 2px solid #e53935; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 24px auto; color: #c21c1c; font-size: 32px; font-weight: bold; font-family: 'Montserrat', sans-serif; }
        .error-heading { font-family: 'Montserrat', sans-serif; font-size: 22px; font-weight: 700; color: #c21c1c; margin-top: 0; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 0.5px; }
        .error-description { font-size: 15px; line-height: 1.6; color: #614141; margin-bottom: 28px; font-weight: 400; }
        .diagnostic-trace-container { background: #fff5f5; border: 1px dashed #e53935; border-radius: 6px; padding: 15px; text-align: left; margin-bottom: 30px; }
        .trace-label { font-family: 'Montserrat', sans-serif; font-size: 11px; font-weight: 700; color: #810b38; text-transform: uppercase; display: block; margin-bottom: 4px; letter-spacing: 0.5px; }
        .trace-message { font-family: monospace; font-size: 13px; color: #c21c1c; word-break: break-word; line-height: 1.4; }
        .btn-return-fix { display: inline-block; background-color: #810b38; color: #ffffff; font-family: 'Montserrat', sans-serif; font-weight: 700; font-size: 14px; text-decoration: none; padding: 14px 32px; border-radius: 6px; text-transform: uppercase; letter-spacing: 0.5px; transition: all 0.2s ease; box-shadow: 0 4px 12px rgba(129,11,56,0.2); }
        .btn-return-fix:hover { background-color: #541212; transform: translateY(-1px); box-shadow: 0 6px 15px rgba(84,18,18,0.3); }
        @keyframes cardSlideUp { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }
    </style>
</head>
<body>
<div class='error-dashboard-card'>
    <div class='chili-alert-badge'>!</div>
    <div class='error-heading'>${not empty param.heading ? param.heading : 'Error Detected'}</div>
    <div class='error-description'>${not empty param.desc ? param.desc : 'Something went wrong while processing your calculation. Please check your inputs and try again.'}</div>
    <div class='diagnostic-trace-container'>
    <span class='trace-label'>
        ${not empty requestScope.label ? requestScope.label : (not empty param.label ? param.label : 'Diagnostic Code:')}
    </span>
    
    <span class='trace-message'>
        ${not empty requestScope.message ? requestScope.message : (not empty param.message ? param.message : 'Unknown exception trace.')}
    </span>
</div>
    <a href='index.jsp' class='btn-return-fix'>← Back</a>
</div>
</body>
</html>