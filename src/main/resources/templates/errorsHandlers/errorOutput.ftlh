<#macro errorOutput errorMessage>
    <#if errorMessage?has_content>
        <div class="d-flex flex-grow-1 justify-content-center alert alert-danger alert-dismissible fade show">
            <strong>Помилка!&nbsp;</strong> ${errorMessage!}
            <button type="button" class="close" data-dismiss="alert">&times;</button>
        </div>
    </#if>
</#macro>