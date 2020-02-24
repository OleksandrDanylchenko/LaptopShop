<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Type editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Зміна назви типу:</p>
    <form method="post" action="/type/edit/${editType.id}" class="form-inline needs-validation w-100" novalidate>
        <label class="sr-only" for="typeName">Назва типу</label>
        <input type="text" class="form-control flex-grow-1" name="name" value="${editType.name!}"
               placeholder="Назва типу" required/>
        <button type="submit" class="btn btn-primary ml-md-2 my-2">Зберегти</button>
        <div class="invalid-feedback">Назва типу не може бути пустою!</div>
    </form>
</@pad.page>