<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro buyingAdd>
    <div class="modal fade" id="buyingAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="buyingAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="buyingAddLabel">Додати нову покупку</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if basketIds?has_content>
                    <#if laptopModels?has_content>
                        <div class="modal-body">
                            <form method="post" id="buyingAddForm" action="/buying/add"
                                  class="form-group needs-validation w-100" novalidate>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="basketId">ID кошику:</label>
                                        <select class="custom-select" id="basketSelect" name="basketId" required>
                                            <option value="" selected disabled></option>
                                            <#list basketIds as basketId>
                                                <option>${basketId}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано ID кошику!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Модель ноутбуку:</label>
                                        <select class="custom-select" id="laptopSelect" name="laptopModel" required>
                                            <option value="" selected disabled></option>
                                            <#list laptopModels as laptopModel>
                                                <option>${laptopModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель ноутбуку!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="totalPrice">Загальна ціна:</label>
                                        <input type="number" id="priceInput" min="5000" max="2147483646"
                                               class="form-control" name="totalPrice" required/>
                                        <div class="invalid-feedback">Уведено некоректну ціну! (<5000 aбо >2147483646)</div>
                                    </div>
                                </div>
                            </form>
                            <@fC.inputsCleaner modalId="buyingAddModal"
                            inputIds=["priceInput"]/>
                            <@sC.selectsCleaner modalId="buyingAddModal"
                            selectIds=["basketSelect", "laptopSelect"]/>
                            <@fSc.fieldsStatusCleaner modalId="buyingAddModal" formId="buyingAddForm"/>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                            <button type="submit" form="buyingAddForm" class="btn btn-danger">Додати</button>
                        </div>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не знайдено жодного ноутбуку.</div>
                                <div class="h3 text-center">
                                    <a href="/laptop">Додайте новий ноутбук</a> для створення покупки.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не знайдено жодного кошику.</div>
                            <div class="h3 text-center">
                                <a href="/basket">Додайте новий кошик</a> для створення покупки.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>