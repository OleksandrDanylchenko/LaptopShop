<#macro page title>
    <!DOCTYPE html>
    <html lang="">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link rel="stylesheet" href="/styles/landingStyle.css">
    </head>
    <body>
    <div><a href="/">Головна сторінка</a> </div>
    <#nested>
    </body>
    </html>
</#macro>