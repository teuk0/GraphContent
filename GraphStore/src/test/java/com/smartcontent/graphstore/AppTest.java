package com.smartcontent.graphstore;

import com.smartcontent.graphstore.api.MineContentType;
import com.smartcontent.graphstore.api.MineStorage;
import com.smartcontent.graphstore.impl.MineStorageImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testBase(){
        MineStorage ms = new MineStorageImpl("bobby14");
        ms.createContentType("type");
        MineContentType mct=ms.getType("type");
        long time = System.currentTimeMillis();
        mct.createContent("bob", "fr", "En effet, la prospérité ne se limite pas à sa définition économique : elle adresse toutes les conditions du bonheur. Force est cependant de constater que la croissance économique est de moins en moins synonyme de prospérité, du moins dans les pays développés où les conditions matérielles du bonheur (manger à sa faim, avoir un toit, vivre en sécurité, etc.) sont globalement satisfaites.");
        System.out.println(System.currentTimeMillis()-time);
                    mct.createContent("bob1", "fr","Soulever les risques liés aux technologies : bioterrorisme, cybercriminalité et surtout chômage… Comme au temps des révolutions agricoles, industrielles et des services, cette 4e révolution va mettre encore plus de monde au chômage avec des problématiques de reconversion certaines.");
            System.out.println(System.currentTimeMillis()-time);
            mct.createContent("bob2", "fr","Il faut sûrement dissocier rémunération matérielle et reconnaissance. Imaginer peut-être un système monétaire double : l’un à somme nulle, permettant d’acquérir et d’échanger des biens matériels dans les limites écologiquement acceptables, l’autre à somme non nulle permettant de valoriser la reconnaissance et la contribution de chacun à la communauté humaine… Les nouveaux indicateurs d’influence tels que Klout, en vogue sur les réseaux sociaux, sont peut-être les prémisses de ce nouveau système monétaire ?");
            System.out.println(System.currentTimeMillis()-time);
            mct.createContent("bob3", "fr","Technologies de l’information : elles n’ont pas fini leur croissance exponentielle. Les intelligences artificielles vont dépasser l’homme vers 2023, et même toute l’intelligence de l’humanité d’ici le milieu du siècle, facilitant l’éducation des enfants à travers le monde et promouvant la liberté d’expression sur toute la planète.");
            System.out.println(System.currentTimeMillis()-time);
            mct.createContent("bob4", "fr","Énergie : solaire, algo-carburant, batterie au sodium, nucléaire de 4e génération et réseaux intelligents devraient permettre de fournir de l’énergie pour tous les habitants et pour tous les usages, tout en réduisant ou en captant les émissions de CO2. Il ne faut pas oublier que le soleil fournit en une heure autant d’énergie que toute la consommation annuelle mondiale.");
            System.out.println(System.currentTimeMillis()-time);
            mct.createContent("bob5", "fr","Eau : les technologies de désalinisation, les toilettes sèches et les réseaux intelligents devraient permettre de résoudre la question de l’alimentation en eau ainsi que les problématiques sanitaires qui y sont liées (cause de nombreuses maladies et de morts prématurées). Il ne faut pas perdre de vue que 70 % de la surface de la Terre est recouverte d’eau.");
            System.out.println(System.currentTimeMillis()-time);
            mct.createContent("bob6", "fr","Alimentation : ferme verticale, permaculture ou encore viande de culture in vitro devraient permettre de nourrir les neuf milliards d’habitants prévus vers 2050, tout en limitant les émissions de gaz à effet de serre liée à l’agriculture intensive, au transport et à l’élevage.");
            System.out.println(System.currentTimeMillis()-time);
            ms.getContent("bob6").getClosestInferedContent();
            System.out.println(System.currentTimeMillis()-time);
    }
}
