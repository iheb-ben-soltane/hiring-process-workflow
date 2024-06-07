package tn.enit.service;

public class EvalCondidature {

    public boolean evalCondidature(final String expCondidat) {
        System.out.println("le condidat a " + expCondidat + " annees d'experience");
        final String confirmation = String.valueOf(System.currentTimeMillis());
        System.out.println("condidature " + expCondidat);
        final int exp = Integer.parseInt(expCondidat);

        try {
            if (exp >= 2) {
                System.out.println("condidature acceptee " + confirmation);
                return true;
            } else {
                System.out.println("condidature refusé" + confirmation);
                return false;
            }
        } catch (Exception e) {
            System.out.println("prolème avec la  " + confirmation);
            throw new RuntimeException(e);
        }

    }
}