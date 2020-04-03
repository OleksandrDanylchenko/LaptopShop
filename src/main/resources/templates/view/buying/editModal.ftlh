<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro buyingEdit id currentBasketId currentLaptopModel currentTotalPrice>
    <div class="modal fade" id="buyingEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="buyingEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="buyingEditLabel${id}">Змінити інформацію про покупку</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/buying/edit/${id}" id="buyingEditForm${id}"
                          class="form-group needs-validation w-100" novalidate>
                        <#if basketIds?has_content>
                            <div class="form-row mb-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="basketSelect${id}">ID кошику:</label>
                                    <select class="custom-select" id="basketSelect${id}" name="editBasketId" required>
                                        <#list basketIds as basketId>
                                            <option value="${basketId}">${basketId}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано ID кошику!</div>
                                </div>
                            </div>
                        </#if>

                        <#if laptopModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="laptopSelect${id}">Модель ноутбуку:</label>
                                    <select class="custom-select" id="laptopSelect${id}" name="editLaptopModel" required>
                                        <#list laptopModels as laptopModel>
                                            <option value="${laptopModel}">${laptopModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель ноутбуку!</div>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="totalPriceInput${id}">Загальна ціна:</label>
                                <input type="number" min="5000" max="2147483646"
                                       id="totalPriceInput${id}" class="form-control" name="editTotalPrice" required/>
                                <div class="invalid-feedback">Уведено некоректну ціну! (<5000 aбо >2147483646)</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@fSc.fieldsStatusCleaner modalId="buyingEditModal${id}" formId="buyingEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#buyingEditModal${id}").on("show.bs.modal", function () {
                            $("#basketSelect${id}").val("${currentBasketId}").change();
                            $("#laptopSelect${id}").val("${currentLaptopModel}").change();
                            $("#totalPriceInput${id}").val("${currentTotalPrice}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="buyingEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>