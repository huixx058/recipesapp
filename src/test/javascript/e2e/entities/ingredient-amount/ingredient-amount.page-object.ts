import { element, by, ElementFinder } from 'protractor';

export class IngredientAmountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-ingredient-amount div table .btn-danger'));
    title = element.all(by.css('jhi-ingredient-amount div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class IngredientAmountUpdatePage {
    pageTitle = element(by.id('jhi-ingredient-amount-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    ingredientAmountInput = element(by.id('field_ingredientAmount'));
    ingredientUnitInput = element(by.id('field_ingredientUnit'));
    recipeeSelect = element(by.id('field_recipee'));
    ingredientSelect = element(by.id('field_ingredient'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setIngredientAmountInput(ingredientAmount) {
        await this.ingredientAmountInput.sendKeys(ingredientAmount);
    }

    async getIngredientAmountInput() {
        return this.ingredientAmountInput.getAttribute('value');
    }

    async setIngredientUnitInput(ingredientUnit) {
        await this.ingredientUnitInput.sendKeys(ingredientUnit);
    }

    async getIngredientUnitInput() {
        return this.ingredientUnitInput.getAttribute('value');
    }

    async recipeeSelectLastOption() {
        await this.recipeeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async recipeeSelectOption(option) {
        await this.recipeeSelect.sendKeys(option);
    }

    getRecipeeSelect(): ElementFinder {
        return this.recipeeSelect;
    }

    async getRecipeeSelectedOption() {
        return this.recipeeSelect.element(by.css('option:checked')).getText();
    }

    async ingredientSelectLastOption() {
        await this.ingredientSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async ingredientSelectOption(option) {
        await this.ingredientSelect.sendKeys(option);
    }

    getIngredientSelect(): ElementFinder {
        return this.ingredientSelect;
    }

    async getIngredientSelectedOption() {
        return this.ingredientSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class IngredientAmountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-ingredientAmount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-ingredientAmount'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
