<#macro errorOutput errorMessage>
    <#if errorMessage?has_content>
        <div class="alert alert-danger alert-dismissible fade show">
            <strong>Помилка!</strong> ${errorMessage!}
            <button type="button" class="close" data-dismiss="alert">&times;</button>
        </div>
    </#if>
</#macro>