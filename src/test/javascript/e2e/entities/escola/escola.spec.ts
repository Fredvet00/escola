import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EscolaComponentsPage, EscolaDeleteDialog, EscolaUpdatePage } from './escola.page-object';

const expect = chai.expect;

describe('Escola e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let escolaComponentsPage: EscolaComponentsPage;
  let escolaUpdatePage: EscolaUpdatePage;
  let escolaDeleteDialog: EscolaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Escolas', async () => {
    await navBarPage.goToEntity('escola');
    escolaComponentsPage = new EscolaComponentsPage();
    await browser.wait(ec.visibilityOf(escolaComponentsPage.title), 5000);
    expect(await escolaComponentsPage.getTitle()).to.eq('escolaApp.escola.home.title');
    await browser.wait(ec.or(ec.visibilityOf(escolaComponentsPage.entities), ec.visibilityOf(escolaComponentsPage.noResult)), 1000);
  });

  it('should load create Escola page', async () => {
    await escolaComponentsPage.clickOnCreateButton();
    escolaUpdatePage = new EscolaUpdatePage();
    expect(await escolaUpdatePage.getPageTitle()).to.eq('escolaApp.escola.home.createOrEditLabel');
    await escolaUpdatePage.cancel();
  });

  it('should create and save Escolas', async () => {
    const nbButtonsBeforeCreate = await escolaComponentsPage.countDeleteButtons();

    await escolaComponentsPage.clickOnCreateButton();

    await promise.all([escolaUpdatePage.setNomeInput('nome')]);

    expect(await escolaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');

    await escolaUpdatePage.save();
    expect(await escolaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await escolaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Escola', async () => {
    const nbButtonsBeforeDelete = await escolaComponentsPage.countDeleteButtons();
    await escolaComponentsPage.clickOnLastDeleteButton();

    escolaDeleteDialog = new EscolaDeleteDialog();
    expect(await escolaDeleteDialog.getDialogTitle()).to.eq('escolaApp.escola.delete.question');
    await escolaDeleteDialog.clickOnConfirmButton();

    expect(await escolaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
