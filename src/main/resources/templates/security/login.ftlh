<#import "../parts/htmlPad.ftlh" as htmlPad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>

<@htmlPad.page "Login">
    <#if (RequestParameters.error)??>
        <@eO.errorOutput"Неправильний логін чи пароль!"/>
    </#if>
    <p class="h1">Автентифікація:</p>
    <form method="post" action="/login" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="loginInput">Логін:</label>
                <input type="text" id="loginInput" class="form-control" name="username"
                       value="${RequestParameters.login!}" required/>
                <div class="invalid-feedback">Логін уведено некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="passwordInput">Пароль:</label>
                <input type="password" id="passwordInput" class="form-control" name="password"
                       value="${RequestParameters.password!}" required/>
                <div class="invalid-feedback">Пароль уведено некоректно!</div>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" class="btn btn-danger float-right" value="Увійти"/></div>
    </form>
    <@eB.emptyBreaker inputIds=["loginInput", "passwordInput"]/>
</@htmlPad.page>