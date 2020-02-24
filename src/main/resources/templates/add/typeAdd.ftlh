<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Type adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати новий тип ноутбуків:</p>
    <form method="post" action="/type/add" class="form-inline needs-validation w-100" novalidate>
        <label class="sr-only" for="newTypeName">Назва нового типу</label>
        <input type="text" class="form-control flex-grow-1" name="name" value="${RequestParameters.name!}"
               placeholder="Назва нового типу" required/>
        <button type="submit" class="btn btn-primary ml-md-2 my-2">Додати</button>
        <div class="invalid-feedback">Назва нового типу не може бути пустою!</div>
    </form>
</@pad.page>