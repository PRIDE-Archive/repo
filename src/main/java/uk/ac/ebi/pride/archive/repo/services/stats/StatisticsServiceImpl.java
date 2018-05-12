package uk.ac.ebi.pride.archive.repo.services.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.archive.repo.repos.assay.AssayRepository;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;

/**
 * @author Rui Wang
 * @version $Id$
 *
 * todo: update to retrieve additional statistics
 */
@Service
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {

    private AssayRepository assayRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public StatisticsServiceImpl(AssayRepository assayRepository, ProjectRepository projectRepository) {
        this.assayRepository = assayRepository;
        this.projectRepository = projectRepository;
    }

    public StatisticsSummary getLatestStatistics() {
        StatisticsSummary res = new StatisticsSummary();

        res.setNumAssay(assayRepository.count());
        res.setNumProjects(projectRepository.count());
//        res.setNumTotalSpectra(assayRepository.getTotalSpectraCount());
//        res.setNumIdentifiedSpectra(assayRepository.getIdentifiedSpectraCount());
//        res.setNumPeptides(assayRepository.getPeptideCount());
//        res.setNumUniquePeptides(assayRepository.getUniquePeptideCount());
//        res.setNumProteins(assayRepository.getProteinCount());

        return res;
    }
}
